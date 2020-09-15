/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tuyetnta.object;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import tuyetnta.dtos.PromotionDTO;

/**
 *
 * @author tuyet
 */
public class Cart implements Serializable {

    private Map<String, PromotionDTO> cart;

    public Map<String, PromotionDTO> getCart() {
        return cart;
    }

    public boolean addToCart(PromotionDTO dto) {
        if (cart == null) {
            cart = new HashMap<>();
        }
        if(this.cart.containsKey(dto.getUser().getId())){
            return true;
        }//đã tồn tại trong cart
        dto.setRank(5);
        this.cart.put(dto.getUser().getId(), dto);
        return false;
    }
    public void update(PromotionDTO dto){
        if(this.cart.containsKey(dto.getUser().getId())){
            this.cart.get(dto.getUser().getId()).setRank(dto.getRank());
        }
    }
    public void remove(String id){
        if(this.cart.containsKey(id)){
            this.cart.remove(id);
        }
    }
}
