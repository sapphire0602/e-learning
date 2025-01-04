package com.ahmad.e_learning.model;

import com.ahmad.e_learning.enums.UserRoles;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Entity
public class Role {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;

        @Enumerated(EnumType.STRING)
        private UserRoles name;

        public Role(int id, UserRoles name) {
                this.id = id;
                this.name = name;
        }

        public Role() {
        }

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        public UserRoles getName() {
                return name;
        }

        public void setName(UserRoles name) {
                this.name = name;
        }
}
