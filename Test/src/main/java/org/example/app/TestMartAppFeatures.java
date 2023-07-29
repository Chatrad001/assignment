package org.example.app;

import org.example.model.contract.Cart;
import org.example.model.contract.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class TestMartAppFeatures {

	private static final Logger logger = LoggerFactory.getLogger(TestMartAppFeatures.class);
	public List<Cart> carts;

	/**
	 * Prints the titles of all products that have a rating less than or equal to the provided criteria.
	 *
	 * @param rating The rating threshold.
	 */
	public void getProductTitlesByWorseRating(double rating) {
		List<String> productTitles = carts.stream()
				.flatMap(cart -> cart.getProducts().stream())
				.filter(product -> product.getRating() <= rating)
				.map(Product::getTitle)
				.toList();

		logger.info("Products with rating less than or equal to " + rating + ":");
		for (String title : productTitles) {
			logger.info(title);
		}
	}

	/**
	 * Returns the cart with the highest total value.
	 *
	 * @returns The cart with the highest total value.
	 */
	public Cart getCartWithHighestTotal() {
		return carts.stream()
				.max(Comparator.comparing(Cart::getTotalValue))
				.orElse(null);
	}

	/**
	 * Returns the cart with the lowest total value.
	 *
	 * @returns The cart with the lowest total value.
	 */
	public Cart getCartWithLowestTotal() {
		return carts.stream()
				.min(Comparator.comparing(Cart::getTotalValue))
				.orElse(null);
	}

	/**
	 * Enriches the product information in a user's cart by adding product images.
	 * The current product information in a cart has limited fields.
	 * This method adds the `images` field for each product in a given user's cart.
	 * Note: This method only applies to the first element from the `carts[]` JSON response.
	 *
	 * @param userId The ID of the user whose cart's product information will be enriched.
	 * @returns A list of products with enriched information in the user's cart.
	 */
	public List<Product> addProductImagesToUserCart(Integer userId) {
		List<Product> products = new ArrayList<>();
		if (!carts.isEmpty()) {
			Cart userCart = carts.get(0);

			for (Product product : userCart.getProducts()) {
				String imageUrl = "https://example.com/product_images/" + product.getId() + ".jpg";

				Product enrichedProduct = new Product(
						product.getId(),
						product.getTitle(),
						product.getDescription(),
						product.getPrice(),
						product.getDiscountPercentage(),
						product.getRating(),
						product.getBrand(),
						product.getCategory(),
						product.getThumbnail(),
						List.of(imageUrl)
				);
				products.add(enrichedProduct);
			}
		}
		return products;
	}
}
