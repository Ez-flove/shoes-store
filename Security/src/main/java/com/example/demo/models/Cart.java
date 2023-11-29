package com.example.demo.models;
import com.example.demo.repository.CartItemRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Builder
@AllArgsConstructor

public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL)
    private List<CartItem> cartItems;
    public Cart(){
        cartItems = new ArrayList<>();
    }
    public void AddtoCart(CartItem cartItem){
        cartItems.add(cartItem);
        cartItem.setCart(this);
    }
    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", user=" + (user != null ? "User{id=" + user.getId() + ", username='" + user.getUsername() + "'}" : "null") +
                ", cartItems=" + cartItems +
                "}";
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
