; (require '[patcher.core :refer [apply-patch]])

(defn init-team
    [teams id]
    "Creates a taxi team"
    (atom (swap! teams assoc (keyword id) {
        :from ""
        :to ""
        :time 0
        :cost 0
        :messages []
        :passengers []
    }))
)

(defn update-team
    [teams id new-team]
    "Updates an existing team"
    (let [id-keyword (keyword id)]
        (atom (-> teams
            (swap! assoc id-keyword (
                merge (get @teams id-keyword) new-team
            ))
        ))
    )
)

(defn join-team
    [teams id name]
    "Join existing team"
    (let [id-keyword (keyword id)
          team (get @teams id-keyword)
          passengers (get team :passengers)
          new-passengers (conj passengers name)]
        (-> teams
            (update-team id (merge team {:passengers new-passengers}))
        )
    )
)

(defn send-message
    [teams id message]
    "Send message to the team"
    (let [id-keyword (keyword id)
          team (get @teams id-keyword)
          messages (get team :messages)
          new-messages (conj messages message)]
        (-> teams
            (update-team id (merge team {:messages new-messages}))
        )
    )
)

; Some tests
(print (init-team (atom {}) "123456789"))
(print "\n")

(print (-> (init-team (atom {}) "123456789")
           (update-team "123456789" {:from "Skoltech" :to "Moscow"})
       )
)
(print "\n")

(print (-> (init-team (atom {}) "123456789")
           (update-team "123456789" {:from "Skoltech" :to "Moscow"})
           (join-team "123456789" "Artem")
           (join-team "123456789" "Serge")
       )
)
(print "\n")

(print (-> (init-team (atom {}) "123456789")
           (update-team "123456789" {:from "Skoltech" :to "Moscow"})
           (join-team "123456789" "Artem")
           (join-team "123456789" "Serge")
           (send-message "123456789" "Serge: Hello, Artem!")
           (send-message "123456789" "Serge: What's the deadline for HW1?")
           (send-message "123456789" "Artem: It has expired already...")
           (send-message "123456789" "Serge: F*ck!")
       )
)
(print "\n")
