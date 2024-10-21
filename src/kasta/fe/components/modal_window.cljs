(ns kasta.fe.components.modal-window
  (:require [rum.core :as rum]
            [kasta.fe.logic.campaigns-logic :refer [load-products]]))

(rum/defcs modal-window < (rum/local [] ::products)
                          (rum/local false ::loading)
  { :did-mount (fn [state]
                 (let [campaign-id (second (:rum/args state))]
                   (reset! (::loading state) true)
                   (load-products campaign-id
                                  (fn [data]
                                    (reset! (::products state) data)
                                    (reset! (::loading state) false))))
                 state) }
  [state show-modal campaign-id on-close]
  (let [products (::products state)
        loading (::loading state)]
    (when @show-modal
      [:div.modal
       [:div.modal-content
        (if @loading
          [:p "Завантаження продуктів..."]
          (if (empty? @products)
            [:p "Продуктів не знайдено"]
            [:ul
             (for [product-id @products]
               ^{:key product-id}
               [:li product-id])]))
        [:button {:on-click (fn [e] 
                              (.stopPropagation e)
                              (on-close))} "Закрити"]]])))