(ns kasta.fe.common.utils)

(defn js->clj-conversion [js-obj]
  (if (and (array? js-obj) (every? object? js-obj))
    (js->clj js-obj :keywordize-keys true)
    []))

(defn active-campaigns [campaigns]
  (let [filtered-campaigns
        (if (and (vector? campaigns) (seq campaigns))
          (let [now (js/Date.)]
            (filter
             (fn [campaign]
               (let [start-date (js/Date. (:starts_at campaign))
                     end-date   (js/Date. (:finishes_at campaign))]
                 (and (< (.getTime start-date) (.getTime now))
                      (> (.getTime end-date) (.getTime now)))))
             campaigns))
          [])]
    filtered-campaigns))