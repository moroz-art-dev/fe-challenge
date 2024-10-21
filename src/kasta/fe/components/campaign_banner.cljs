(ns kasta.fe.components.campaign-banner
  (:require [rum.core :as rum]
            [kasta.fe.common.config :refer [image-url placeholder-image-url]]
            [kasta.fe.common.utils :refer [format-date]]
            [kasta.fe.components.modal-window :refer [modal-window]]))

(rum/defcs campaign-banner < (rum/local false ::show-modal)
  [state campaign]
  (let [show-modal (::show-modal state)
        toggle-modal (fn [] 
                       (swap! show-modal not))]

    [:div.banner
     {:on-click toggle-modal}
 (let [image (or (:now_image campaign) placeholder-image-url)]
   [:img {:src (image-url image :small)
          :alt (:name campaign)
          :loading "lazy"}])
 [:div.description
 [:div.left-column
  [:h2 (:name campaign)]
  [:p (:description campaign)]]
 [:div.right-column
 [:p "Початок акції: " (format-date (:starts_at campaign))]
 [:p "Кінець акції: " (format-date (:finishes_at campaign))]]]

     (when @show-modal
       (modal-window show-modal (:id campaign) toggle-modal))]))