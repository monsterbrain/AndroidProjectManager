/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.monsterbrain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Monster Brain
 */
@AllArgsConstructor
public class ProjectModel {
    @Getter
    @Setter
    private String folderName;
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private String displayName;
}
