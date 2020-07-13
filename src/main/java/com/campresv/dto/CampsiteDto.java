package com.campresv.dto;

import javax.persistence.*;

@Entity
@Table(name = "CAMP_SITE")
public class CampsiteDto {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name ="SITE_NUMBER")
    private Long siteNumber;

    @Column(name = "CAPACITY")
    private Integer capacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteNumber() {
        return siteNumber;
    }

    public void setSiteNumber(Long siteNumber) {
        this.siteNumber = siteNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}
