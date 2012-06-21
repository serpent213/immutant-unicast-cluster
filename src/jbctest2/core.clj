(ns jbctest2.core
  (:require [clojure.string :as string]
            [immutant.cache :as cache]
            [immutant.messaging :as msg]))

(defonce messages (atom []))

(defonce mcache (cache/cache "messages" :mode :replicated, :seed {:m []}))

(defn ring-handler
  [{:keys [request-method body] :as req}]
  (println req)
  (if (= request-method :get)
    {:status 200,
     :body (str
             "<html><body>"
             "<h1>Cluster test</h1>"
             "<h2>HornetQ</h2>"
             "<ol>"
             (string/join (map #(str "<li>" % "</li>") @messages))
             "</ol>"
             "<h2>Infinispan</h2>"
             "<ol>"
             (string/join (map #(str "<li>" % "</li>") (get mcache :m)))
             "</ol>"
             "<form method='post'>"
             "<input type='text' name='message'>"
             "<input type='submit'>"
             "</form>"
             "</body></html>")}
    (let [msg (slurp body)]
      (msg/publish "/topic/cluster" msg)
      (cache/put mcache :m (conj (get mcache :m) msg))
      {:status 200,
       :body (str
               "<html><body>"
               "<h1>Cluster test</h1>"
               "<p>OK</p>"
               "<p><a href='.'>back</a></p>"
               "</body></html>")})))

(defn message-handler
  [msg]
  (println msg)
  (swap! messages conj msg))
