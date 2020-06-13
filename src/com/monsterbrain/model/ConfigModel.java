/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterbrain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Holds the program wide configuration such as Android Studio Path etc
 * @author Faisal.Rasak
 */
public class ConfigModel {
    @Getter
    @Setter
    private String androidStudioPath;
}
