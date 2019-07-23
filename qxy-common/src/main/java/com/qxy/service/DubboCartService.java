package com.qxy.service;

import java.util.List;

import com.qxy.pojo.Cart;

public interface DubboCartService {

	List<Cart> findCartListByUserId(Long userId);

	void deleteCart(Cart cart);

	void saveCart(Cart cart);

	void updateCartNum(Cart cart);

}
