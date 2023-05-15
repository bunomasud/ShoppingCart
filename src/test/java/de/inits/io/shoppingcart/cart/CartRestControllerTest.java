package de.inits.io.shoppingcart.cart;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.gson.Gson;
import de.inits.io.shoppingcart.cart.applicationservice.service.CartApplicationService;
import de.inits.io.shoppingcart.cart.primaryadatpter.adapter.CartRestControllerAdapter;
import de.inits.io.shoppingcart.cart.primaryadatpter.port.rest.CartItemRequestData;
import de.inits.io.shoppingcart.cart.primaryadatpter.port.rest.CartRequestData;
import de.inits.io.shoppingcart.cart.primaryadatpter.port.rest.CartResponseData;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartRestControllerTest {

    @Autowired
    private CartApplicationService cartApplicationService;
    @Autowired
    private CartRestControllerAdapter cartRestControllerAdapter;
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Gson gson = new Gson();
    private UUID sku1 = UUID.fromString("c687b35c-950c-42c3-89b4-9db08ec34ec5");
    private UUID sku2 = UUID.fromString("c687b35c-951c-42c3-89b4-9db08ec34ec5");
    private UUID sku3 = UUID.fromString("c687b35c-952c-42c3-89b4-9db08ec34ec5");

    @Autowired
    private JdbcTemplate jdbc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        setUpProducts();

    }

    public void setUpProducts() {
        jdbc.execute("insert into product(sku,name,ean,price,stock) values ('" + sku1
                + "', 'product a', 'ean1', 15.5, 3.0)");
        jdbc.execute(
                "insert into product(sku,name,ean,price,stock) values ('" + sku2 + "', 'product b', 'ean2', 5.5, 1.0)");
        jdbc.execute(
                "insert into product(sku,name,ean,price,stock) values ('" + sku3 + "', 'product c', 'ean3', 2, 0)");
    }

    @After
    public void cleanUp() {
        jdbc.execute("DELETE from product");
        jdbc.execute("DELETE from cart_item");
        jdbc.execute("DELETE from cart");
        Assert.assertTrue(jdbc.queryForObject("SELECT count(*) FROM cart", Integer.class) == 0);
        Assert.assertTrue(jdbc.queryForObject("SELECT count(*) FROM cart_item", Integer.class) == 0);
        Assert.assertTrue(jdbc.queryForObject("SELECT count(*) FROM product", Integer.class) == 0);
    }

    @Test
    public void testCreateAndEditCart() throws Exception {
        //create cart
        List<CartItemRequestData> itemRequestDataList = new ArrayList<>();
        itemRequestDataList.add(CartItemRequestData.builder().sku(sku1.toString()).quantity(1).build());
        CartRequestData requestData = CartRequestData.builder()
                .items(itemRequestDataList).build();

        String responseCart = mockMvc.perform(
                        post("/cart/").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(requestData)))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        CartResponseData responseData = gson.fromJson(responseCart, CartResponseData.class);
        Assert.assertEquals(0, responseData.getTotalPrice().compareTo(BigDecimal.valueOf(15.5)));
        Assert.assertEquals(1, responseData.getItems().size());

        itemRequestDataList.add(CartItemRequestData.builder().sku(sku2.toString()).quantity(1).build());
        //edit cart
        mockMvc.perform(
                        patch("/cart/" + responseData.getCartId() + "/").contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(requestData)))
                .andExpect(status().isOk());

        String responseEditedCart = mockMvc.perform(
                        get("/cart/" + responseData.getCartId() + "/")).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        CartResponseData responseEditedData = gson.fromJson(responseEditedCart, CartResponseData.class);
        Assert.assertEquals(0, responseEditedData.getTotalPrice().compareTo(BigDecimal.valueOf(21)));
        Assert.assertEquals(2, responseEditedData.getItems().size());

        //uavailable stock

        itemRequestDataList.add(CartItemRequestData.builder().sku(sku3.toString()).quantity(1).build());
        mockMvc.perform(
                        patch("/cart/" + responseData.getCartId() + "/").contentType(MediaType.APPLICATION_JSON)
                                .content(gson.toJson(requestData)))
                .andExpect(status().isOk());

        String responseEditCartUnavailableItem = mockMvc.perform(
                        get("/cart/" + responseData.getCartId() + "/")).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();

        CartResponseData responseUnavailableAmount = gson.fromJson(responseEditCartUnavailableItem,
                CartResponseData.class);
        Assert.assertEquals(0, responseUnavailableAmount.getTotalPrice().compareTo(BigDecimal.valueOf(21)));
        Assert.assertEquals(2, responseUnavailableAmount.getItems().size());
    }

    @Test
    public void testCheckOutCart() throws Exception {
        List<CartItemRequestData> itemRequestDataList = new ArrayList<>();
        itemRequestDataList.add(CartItemRequestData.builder().sku(sku1.toString()).quantity(1).build());
        CartRequestData requestData = CartRequestData.builder()
                .items(itemRequestDataList).build();

        //create cart
        mockMvc.perform(post("/cart/").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(requestData)))
                .andExpect(status().isOk());

        //set as checked out
        mockMvc.perform(post("/cart/1/setStatus?status=CHECKED_OUT")).andExpect(status().isOk());

        //try to patch with new item
        itemRequestDataList.add(CartItemRequestData.builder().sku(sku2.toString()).quantity(1).build());

        mockMvc.perform(
                        patch("/cart/1/").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(requestData)))
                .andExpect(status().isOk());

        //get cart to check size()
        String responseCart = mockMvc.perform(
                get("/cart/" + 1 + "/")).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        CartResponseData responseData = gson.fromJson(responseCart, CartResponseData.class);
        Assert.assertEquals(1, responseData.getItems().size());

    }

}
