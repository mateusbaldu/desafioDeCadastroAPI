package com.desafio.cadastro.models;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pet")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Pets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private String name;
    @NotNull
    private PetType petType;
    @NotNull
    private PetSex petSex;
    private int age;
    private float weight;
    private String race;
}
