(ns kasta.fe.common.utils)

(defn js->clj-conversion [js-obj]
  (if (array? js-obj)
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

(defn filter-fields [campaigns]
  (map (fn [campaign]
         {:name (:name campaign)
          :description (:description campaign)
          :starts_at (:starts_at campaign)
          :finishes_at (:finishes_at campaign)
          :url (:url campaign)
          :id (:id campaign)
          :now_image (:now_image campaign)})
       campaigns))