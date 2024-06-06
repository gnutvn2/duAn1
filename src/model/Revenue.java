/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Revenue {
    private String productDetailCode;
    private String productCode;
    private int sold;
    private String productName;
    private String materialName;
    private String colorName;
    private int numberRemain;
    
    public Object[] toDataRow() {
        return new Object[] {
            productDetailCode,productCode,sold,productName,materialName,colorName,numberRemain
        };
    }
}
