(ns kasta.fe.main
  (:require [rum.core :as rum]
            [kasta.fe.components.error-boundary :refer [error-boundary]]
            [kasta.fe.components.campaigns :refer [campaign-list]]))

(rum/defc Root []
  [:div
   [:h1 "Акції"]
   (error-boundary
    (campaign-list))])

(defn ^:export trigger-render []
  (rum/mount (Root) (js/document.getElementById "content")))