package de.inits.io.shoppingcart.product;

import com.google.gson.Gson;
import de.inits.io.shoppingcart.product.applicationservice.service.ProductApplicationService;
import de.inits.io.shoppingcart.product.primaryadatpter.port.adapter.ProductRestControllerAdapter;
import de.inits.io.shoppingcart.product.primaryadatpter.port.rest.ProductData;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInventoryControllerTest {

    @Autowired
    private ProductRestControllerAdapter adapter;
    @Autowired
    private ProductApplicationService service;

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Gson gson;


    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        gson = new Gson();

    }

    @Test
    public void testAddAndDeleteProduct() throws Exception {
        UUID prodSku = UUID.randomUUID();
        ProductData newProduct = ProductData.builder().sku(prodSku).price(BigDecimal.valueOf(5)).name("Product A")
                .stock(5).eans(List.of("EAN1")).build();
        mockMvc.perform(
                        post("/inventory/product").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(newProduct)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/inventory/products").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(jsonPath("$.products[0].sku", is(newProduct.getSku().toString()))).
                andExpect(jsonPath("$.products[0].price", is(5.0))).
                andExpect(jsonPath("$.products[0].name", is("Product A"))).
                andExpect(jsonPath("$.products[0].stock", is(5)));
        
        mockMvc.perform(delete("/inventory/product/" + prodSku + "/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String response = mockMvc.perform(get("/inventory/product/" + prodSku + "/").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ProductData data = gson.fromJson(response, ProductData.class);
        Assert.assertEquals(ProductData.builder().build(), data); //hack, for now..


    }

    @Test
    public void editProductsInventory() throws Exception {

        UUID editProdSku = UUID.randomUUID();
        ProductData editProduct = ProductData.builder().sku(editProdSku).price(BigDecimal.valueOf(10)).name("Product B")
                .stock(10).eans(List.of("EAN1")).build();
        mockMvc.perform(
                        post("/inventory/product").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(editProduct)))
                .andExpect(status().isOk());

        String response = mockMvc.perform(get("/inventory/product/" + editProdSku + "/")).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ProductData responseProduct = gson.fromJson(response, ProductData.class);

        Assert.assertEquals(editProduct.getName(), responseProduct.getName());
        Assert.assertEquals(editProduct.getSku(), responseProduct.getSku());
        Assert.assertEquals(editProduct.getPrice().compareTo(responseProduct.getPrice()), 0);
        Assert.assertEquals(editProduct.getEans(), responseProduct.getEans());
        Assert.assertEquals(editProduct.getStock(), responseProduct.getStock());

        editProduct.setName("Product b-edited");
        editProduct.setPrice(BigDecimal.valueOf(15));
        editProduct.setStock(5);
        editProduct.setEans(List.of("EAN-NEW"));
        String editJson = gson.toJson(editProduct);

        mockMvc.perform(patch("/inventory/product").contentType(MediaType.APPLICATION_JSON).content(editJson))
                .andExpect(status().isOk());

        String responseAfterEdit = mockMvc.perform(get("/inventory/product/" + editProdSku + "/"))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ProductData responseEditProduct = gson.fromJson(responseAfterEdit, ProductData.class);

        Assert.assertEquals("Product b-edited", responseEditProduct.getName());
        Assert.assertEquals(editProduct.getSku(), responseEditProduct.getSku());
        Assert.assertEquals(BigDecimal.valueOf(15).compareTo(responseEditProduct.getPrice()), 0);
        Assert.assertEquals(editProduct.getEans(), responseEditProduct.getEans());

        mockMvc.perform(delete("/inventory/product/" + editProdSku + "/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String responseDel = mockMvc.perform(get("/inventory/product/" + editProdSku + "/").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        ProductData data = gson.fromJson(responseDel, ProductData.class);
        Assert.assertEquals(ProductData.builder().build(), data); //hack, for now..

    }


}
