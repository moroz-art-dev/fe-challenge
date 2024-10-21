(ns kasta.fe.common.config)
(def base-api-url "/api/v2")
(defn api-url-campaigns []
  (str base-api-url "/campaigns"))
(defn api-url-products [campaign-id]
  (str base-api-url "/products/list?campaign_id=" campaign-id))

(def placeholder-image-url "https://cdn.kasta.ua/static/img/logo.svg?v=84234c")
(def image-base-url "https://cdn.kasta.ua/imgw/loc/")
(def image-sizes
  {:small "420x231"
   :medium "640x352"
   :large "860x482"
   :xlarge "1280x704"})
(defn image-url [image size]
  (str image-base-url (get image-sizes size) "/" image))