package com.desafio.cadastro.models;

import com.desafio.cadastro.enums.PetSex;
import com.desafio.cadastro.enums.PetType;
import jakarta.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PetType petType;

    @Enumerated(EnumType.STRING)
    @Column(name = "sex")
    private PetSex petSex;

    private float age;
    private float weight;
    private String race;
}
