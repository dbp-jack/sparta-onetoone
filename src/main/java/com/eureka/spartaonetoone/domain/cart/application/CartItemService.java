package com.eureka.spartaonetoone.domain.cart.application;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eureka.spartaonetoone.domain.cart.application.dtos.request.CartItemCreateRequestDto;
import com.eureka.spartaonetoone.domain.cart.application.exceptions.CartException;
import com.eureka.spartaonetoone.domain.cart.application.exceptions.CartItemException;
import com.eureka.spartaonetoone.domain.cart.domain.Cart;
import com.eureka.spartaonetoone.domain.cart.domain.CartItem;
import com.eureka.spartaonetoone.domain.cart.domain.repository.CartItemRepository;
import com.eureka.spartaonetoone.domain.cart.domain.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemService {

	private final int MIN_QUANTITY = 1;

	private final CartRepository cartRepository;
	private final CartItemRepository cartItemRepository;

	@Transactional
	public void saveCartItem(UUID cartId, CartItemCreateRequestDto requestDto) {
		Cart cart = cartRepository.findById(cartId)
			.orElseThrow(CartException.NotFound::new);

		CartItem cartItem = createCartItem(cart, requestDto);
		cart.addCartItem(cartItem);

		cartItemRepository.save(cartItem);
	}

	private CartItem createCartItem(Cart cart, CartItemCreateRequestDto requestDto) {
		if (requestDto.getQuantity() < MIN_QUANTITY) {
			throw new CartItemException.MinQuantity();
		}

		/*
		 TODO-1 : Product domain에서 상품 정보를 가져와 CartItem 생성하기
		 TODO-2 : 중복된 상품이 있는지 확인하고 중복된 상품이 있다면 수량과 가격만 증가시키기
		 */
		UUID productId = UUID.randomUUID();
		String productName = "상품 이름";
		String productImage = "상품 이미지";
		int productPrice = 10000;
		int quantity = requestDto.getQuantity();
		int price = quantity * productPrice;

		return CartItem.of(cart, productId, productName, productImage, quantity, price);
	}
}
