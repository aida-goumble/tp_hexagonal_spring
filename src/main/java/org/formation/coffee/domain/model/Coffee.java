package org.formation.coffee.domain.model;

import org.formation.coffee.domain.exception.DomainValidationException;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class Coffee {
    public static final int MAX_NAME_LENGTH = 255;

    private final @Nullable CoffeeId id;
    private String name;
    private Price price;
    private final List<Review> reviews;

    private Coffee(@Nullable CoffeeId id, String name, Price price, List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.price = Objects.requireNonNull(price, "Price must not be null");
        this.reviews = List.copyOf(reviews);
    }

    public static Coffee create(String name, Price price) {
        return new Coffee(null, name, price, List.of());
    }

    public static Coffee reconstitute(CoffeeId id, String name, Price price, List<Review> reviews) {
        return new Coffee(Objects.requireNonNull(id, "id must not be null"), name, price, reviews);
    }

    public void rename(String newName) {
        this.name = requireValidName(newName);
    }

    public void changePrice(Price newPrice) {
        this.price = Objects.requireNonNull(newPrice, "price must not be null");
    }

    public boolean isNew(){
        return this.id == null;
    }

    public @Nullable CoffeeId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }
    public List<Review> getReviews() {
        return reviews;
    }

    public static String requireValidName(@Nullable String name) {
        if (name == null || name.isBlank())
            throw new DomainValidationException("Name must not be blank");
        if (name.length() > MAX_NAME_LENGTH)
            throw new DomainValidationException("Name must not be longer than " + MAX_NAME_LENGTH + " characters");
        return name;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) return true;
        return other instanceof Coffee coffee && id != null &&  this.id.equals(coffee.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

    @Override
    public String toString() {
        return "Coffee [id=" + id + ", name=" + name + ", price=" + price + "]";
    }
}
