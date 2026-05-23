package models

import com.example.habiceb_ares_finalproject.R

data class Product(
    val id: Int,
    val name: String,
    val brand: String,
    val description: String,
    val price: Double,
    val rating: Float,
    val imageRes: Int,
    val category: String
)

object ProductRepository {
    val categories = listOf("All", "Hobo Bags", "Leaf Texture", "Vegan Leather", "Minimalist")
    
    val products = listOf(
        Product(1, "Classic Hobo", "HabiCeb", "Everyday essential vegan leather", 1250.0, 4.5f, R.drawable.bag1, "Hobo Bags"),
        Product(2, "Leaf Tote", "NatureLine", "Eco-friendly leaf texture design", 1800.0, 4.8f, R.drawable.bag2, "Leaf Texture"),
        Product(3, "Chic Mini", "SeraPicks", "Modern minimalist daily bag", 950.0, 4.2f, R.drawable.bag3, "Minimalist"),
        Product(4, "Urban Satchel", "HabiCeb", "Premium vegan leather finish", 2100.0, 4.9f, R.drawable.bag4, "Vegan Leather"),
        Product(5, "Vintage Hobo", "OldSchool", "Classic 90s inspired silhouette", 1400.0, 4.0f, R.drawable.bag5, "Hobo Bags")
    )
}
