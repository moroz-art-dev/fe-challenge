(ns kasta.app
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [ring.util.response :as response]
             [ring.middleware.content-type :as content-type]
            [ring.middleware.params :refer [wrap-params]]
            [aleph.http :as http]))


(defn static [{:keys [uri] :as req}]
  (or (some-> (str/replace uri #"^/static/" "")
              (response/resource-response)
              (content-type/content-type-response req)
              (update :body io/input-stream))
      {:status 404}))


(defn campaigns []
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (:body @(http/get "https://kasta.ua/api/v2/campaigns"))})

(defn products [campaign-id]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (:body @(http/get (str "https://kasta.ua/api/v2/products/list?campaign_id=" campaign-id)))})

(defn custom-wrap-params [handler]
  (fn [req]
    (let [params (ring.middleware.params/params-request req)]
      (handler (assoc req :query-params params)))))

(defn app [{:keys [uri query-params] :as req}]
  (cond
    (= "/" uri)
    (static (assoc req :uri "/static/index.html"))

    (str/starts-with? uri "/static/")
    (static req)

    (= "/api/v2/campaigns" uri)
    (campaigns)

    (and (= "/api/v2/products/list" uri)
         (some? (get-in query-params [:params "campaign_id"])))
    (let [campaign-id (get-in query-params [:params "campaign_id"])]
      (products campaign-id))

    :else
    {:status 404
     :body   "not found"}))

(def app-with-params (custom-wrap-params app))