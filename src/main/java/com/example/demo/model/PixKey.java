package com.example.demo.model;

import com.example.demo.enums.PixKeyType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TB_PixKey")
public class PixKey {

    @Id
    protected String pixkey;

    @Column(nullable = false)
    protected PixKeyType type;

    @Version
    @Column(name = "version")
    protected Long version;
}
