(ns kasta.fe.logic.campaigns-logic
  (:require [kasta.fe.common.api :refer [fetch-campaigns fetch-products]]
            [kasta.fe.common.utils :refer [active-campaigns js->clj-conversion filter-fields]]))

(defn load-and-filter-campaigns [set-campaigns]
  (fetch-campaigns
    (fn [campaigns]
      (let [clj-campaigns (js->clj-conversion campaigns)]
        (if (some? clj-campaigns)
          (let [active (active-campaigns clj-campaigns)
                filtered-active (filter-fields active)]
            (set-campaigns filtered-active))
          (js/console.error "Failed to process campaigns"))))))


(defn load-products [campaign-id set-products]
  (fetch-products
    campaign-id
    (fn [products]
      (let [clj-products (js->clj-conversion products)]
        (if (some? clj-products)
          (set-products clj-products)
          (js/console.error "Failed to process products"))))))