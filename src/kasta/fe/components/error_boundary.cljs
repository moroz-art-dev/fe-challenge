(ns kasta.fe.components.error-boundary
  (:require [rum.core :as rum]))

(rum/defc error-boundary [child]
  (try
    child
    (catch js/Error e
      [:div "Error occurred!"])))