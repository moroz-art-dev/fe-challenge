(ns kasta.fe.components.campaign-banner
  (:require [rum.core :as rum]
            [kasta.fe.common.config :refer [image-url placeholder-image-url]]))

(rum/defc campaign-banner [campaign]
  [:div.banner
   (let [image (or (:now_image campaign) placeholder-image-url)]
     [:img {:src (image-url image :small)
            :alt (:name campaign)
            :loading "lazy"}])
   [:h2 (:name campaign)]
   [:p (:description campaign)]
   [:p "Початок акції: " (:starts_at campaign)]
   [:p "Кінець акції: " (:finishes_at campaign)]
   [:p "id: " (:id campaign)]
  ])