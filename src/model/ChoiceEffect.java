/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Set;

/**
 *
 * @author Chaha
 */
public class ChoiceEffect<T> extends Effect<T> {
    public Mask selectionMask;

    @Override
    public boolean authorized() {
        return !createSelectionList().isEmpty();
    }

    @Override
    public void apply(T object, int res) {
        //TO DO !
    }
    
    public Set<T> createSelectionList(){
        return null;
        // TO DO
    }
}
