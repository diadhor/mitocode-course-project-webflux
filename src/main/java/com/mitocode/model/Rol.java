package com.mitocode.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="roles")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
@AllArgsConstructor
@NoArgsConstructor
public class Rol {
    @Id
    @EqualsAndHashCode.Include
    private Integer id;
    @Field
    private String name;

}
