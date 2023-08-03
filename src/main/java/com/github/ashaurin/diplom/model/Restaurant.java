package com.github.ashaurin.diplom.model;

import lombok.*;
import com.github.ashaurin.diplom.util.validation.NoHtml;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Restaurant extends BaseEntity {

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    @NoHtml
    private String name;

    public Restaurant(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
