package com.example.demo.model;

import com.example.demo.enums.PixKeyType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PixKey {

    @Id
    protected String pixkey;

    @Column(nullable = false)
    protected PixKeyType type;
}
