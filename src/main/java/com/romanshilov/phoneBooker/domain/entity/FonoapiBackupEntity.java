package com.romanshilov.phoneBooker.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "phono_api_backup")
@Data
@EqualsAndHashCode
public class FonoapiBackupEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "technology")
    private String technology;

    @Column(name = "_2g_bands")
    private String _2gBands;

    @Column(name = "_3g_bands")
    private String _3gBands;

    @Column(name = "_4g_bands")
    private String _4gBands;

    @Column(name = "phone_name")
    private String phoneName;

}
