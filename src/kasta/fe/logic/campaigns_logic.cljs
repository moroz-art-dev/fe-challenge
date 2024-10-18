(ns kasta.fe.logic.campaigns-logic
  (:require [kasta.fe.common.api :refer [fetch-campaigns]]
            [kasta.fe.common.utils :refer [active-campaigns js->clj-conversion]]))

(defn load-and-filter-campaigns [set-campaigns]
  (fetch-campaigns
    (fn [campaigns]
      (let [clj-campaigns (js->clj-conversion campaigns)]
        (if (some? clj-campaigns)
          (let [active (active-campaigns clj-campaigns)]
            (set-campaigns active))
          (js/console.error "Failed to process campaigns"))))))