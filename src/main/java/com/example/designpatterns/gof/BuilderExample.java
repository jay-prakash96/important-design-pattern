package com.example.designpatterns.gof;

/**
 * Builder Pattern Example
 * Separates the construction of a complex object from its representation.
 * Interview Insight: Used for creating immutable objects with many optional parameters.
 */
/**
 * Builder Pattern Example
 *
 * Definition: Separates the construction of a complex object from its representation.
 *
 * Real-life analogy: Building a user profile with optional fields (email, phone, address).
 *
 * Interview explanation:
 * - Builder is used for creating immutable objects with many optional parameters.
 * - "Suppose you want to create a user profile, but not all fields are always required. Builder pattern makes this easy and readable."
 *
 * Real-life Example: UserProfile builder
 */
public class BuilderExample {
    public static class UserProfile {
        private final String firstName;
        private final String lastName;
        private final Integer age;
        private final String email;
        private final String phone;
        private final String address;

        private UserProfile(Builder builder) {
            this.firstName = builder.firstName;
            this.lastName = builder.lastName;
            this.age = builder.age;
            this.email = builder.email;
            this.phone = builder.phone;
            this.address = builder.address;
        }

        public static class Builder {
            private String firstName;
            private String lastName;
            private Integer age;
            private String email;
            private String phone;
            private String address;

            public Builder firstName(String firstName) { this.firstName = firstName; return this; }
            public Builder lastName(String lastName) { this.lastName = lastName; return this; }
            public Builder age(Integer age) { this.age = age; return this; }
            public Builder email(String email) { this.email = email; return this; }
            public Builder phone(String phone) { this.phone = phone; return this; }
            public Builder address(String address) { this.address = address; return this; }
            public UserProfile build() { return new UserProfile(this); }
        }

        @Override
        public String toString() {
            return "UserProfile{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
}

