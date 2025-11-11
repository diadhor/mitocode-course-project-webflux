package com.mitocode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "dishes")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //Crea la funcion del comparacion entre 2 objetos solo incluyendo el campo especificado con @EqualsAndhasCode.Include
public class Dish {
    @Id
    @EqualsAndHashCode.Include //Hará las comparaciones enter objetos verfificando el ID del objeto y no la referencia asignada al objeto
    private String id;
    @Field//(name="nombre del campo")
    private String name;
    @Field
    private Double price;
    @Field
    private Boolean status;

}
