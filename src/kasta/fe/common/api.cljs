(ns kasta.fe.common.api
  (:require [kasta.fe.common.config :refer [api-url]]))

(defn fetch-campaigns [set-campaigns]
  (-> (js/fetch api-url)
      (.then (fn [response] (.json response)))
      (.then (fn [data]
               (let [items (.-items data)]
                 (when (fn? set-campaigns)
                   (set-campaigns items)))))
      (.catch (fn [error]
                (js/console.error "Error fetching campaigns:" error)))))