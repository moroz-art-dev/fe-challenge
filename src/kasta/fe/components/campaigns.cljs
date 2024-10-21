(ns kasta.fe.components.campaigns
  (:require [rum.core :as rum]
            [kasta.fe.logic.campaigns-logic :refer [load-and-filter-campaigns]]
            [kasta.fe.components.campaign-banner :refer [campaign-banner]]))

(rum/defcs campaign-list < (rum/local [] ::campaigns)
  { :did-mount (fn [state]
                 (load-and-filter-campaigns
                   (fn [data]
                     (reset! (::campaigns state) data)
                     (js/console.log "Loaded campaigns:" (clj->js @(::campaigns state)))))
                 state) }
  [state]
  (let [campaigns (::campaigns state)]
    [:div
     (if (empty? @campaigns)
       [:p "Loading campaigns..."]
       [:div.campaign-grid
        (for [campaign @campaigns]
          ^{:key (:name campaign)}
          (campaign-banner campaign))])]))