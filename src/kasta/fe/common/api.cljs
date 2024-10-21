(ns kasta.fe.common.api
  (:require [kasta.fe.common.config :refer [api-url-campaigns api-url-products]]))

(defn fetch-campaigns [set-campaigns]
  (-> (js/fetch (api-url-campaigns))
      (.then (fn [response] (.json response)))
      (.then (fn [data]
               (let [items (.-items data)]
                 (when (fn? set-campaigns)
                   (set-campaigns items)))))
      (.catch (fn [error]
                (js/console.error "Error fetching campaigns:" error)))))

(defn fetch-products [campaign-id set-products]
  (-> (js/fetch (api-url-products campaign-id))
      (.then (fn [response] (.json response)))
      (.then (fn [data]
               (let [product-ids (.-product_ids data)]
                 (when (fn? set-products)
                   (set-products product-ids)))))
      (.catch (fn [error]
                (js/console.error "Error fetching products:" error)))))