import org.example.app.TestMartAppFeatures;
import org.example.model.contract.Cart;
import org.example.model.contract.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestMartAppFeaturesTest {

    private TestMartAppFeatures testMartAppFeatures;

    @BeforeEach
    public void setUp() {
        testMartAppFeatures = new TestMartAppFeatures();
        testMartAppFeatures.carts = createSampleCarts();
    }

    @Test
    public void testGetProductTitlesByWorseRating() {
        double ratingThreshold = 3.5;
        List<String> expectedTitles = List.of("Product A", "Product D");

        List<String> actualTitles = new ArrayList<>();
        testMartAppFeatures.getProductTitlesByWorseRating(ratingThreshold);

        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream()) {
            @Override
            public void println(String x) {
                actualTitles.add(x);
            }
        });

        assertEquals(expectedTitles, actualTitles);
    }

    @Test
    public void testGetCartWithHighestTotal() {
        Cart expectedHighestTotalCart = testMartAppFeatures.carts.get(2); // Cart C has the highest total value

        Cart actualHighestTotalCart = testMartAppFeatures.getCartWithHighestTotal();

        assertEquals(expectedHighestTotalCart, actualHighestTotalCart);
    }

    @Test
    public void testGetCartWithLowestTotal() {
        Cart expectedLowestTotalCart = testMartAppFeatures.carts.get(0); // Cart A has the lowest total value

        Cart actualLowestTotalCart = testMartAppFeatures.getCartWithLowestTotal();

        assertEquals(expectedLowestTotalCart, actualLowestTotalCart);
    }

    @Test
    public void testAddProductImagesToUserCart() {
        int userId = 1; // Assuming the first cart belongs to user 1
        List<Product> expectedEnrichedProducts = createSampleEnrichedProducts();

        List<Product> actualEnrichedProducts = testMartAppFeatures.addProductImagesToUserCart(userId);

        assertEquals(expectedEnrichedProducts, actualEnrichedProducts);
    }

    // Helper methods to create sample data for testing
    private List<Cart> createSampleCarts() {
        List<Cart> carts = new ArrayList<>();
        Cart cartA = new Cart("A", "Cart A", "Sample Cart A", 1);
        Cart cartB = new Cart("B", "Cart B", "Sample Cart B", 2);
        Cart cartC = new Cart("C", "Cart C", "Sample Cart C", 1);

        // Add products to the carts
        Product productA = new Product("A1", "Product A", "Sample Product A", 100, 0.1, 4.2, "Brand X", "Category 1", "thumbnailA", new ArrayList<>());
        Product productB = new Product("B1", "Product B", "Sample Product B", 150, 0.2, 3.8, "Brand Y", "Category 2", "thumbnailB", new ArrayList<>());
        Product productC = new Product("C1", "Product C", "Sample Product C", 200, 0.0, 4.5, "Brand Z", "Category 1", "thumbnailC", new ArrayList<>());
        Product productD = new Product("D1", "Product D", "Sample Product D", 120, 0.15, 3.9, "Brand X", "Category 2", "thumbnailD", new ArrayList<>());

        cartA.addProduct(productA);
        cartA.addProduct(productB);
        cartB.addProduct(productC);
        cartC.addProduct(productD);

        carts.add(cartA);
        carts.add(cartB);
        carts.add(cartC);

        return carts;
    }

    private List<Product> createSampleEnrichedProducts() {
        List<Product> enrichedProducts = new ArrayList<>();

        Product productA = new Product("A1", "Product A", "Sample Product A", 100, 0.1, 4.2, "Brand X", "Category 1", "thumbnailA", List.of("https://example.com/product_images/A1.jpg"));
        Product productD = new Product("D1", "Product D", "Sample Product D", 120, 0.15, 3.9, "Brand X", "Category 2", "thumbnailD", List.of("https://example.com/product_images/D1.jpg"));

        enrichedProducts.add(productA);
        enrichedProducts.add(productD);

        return enrichedProducts;
    }
}
