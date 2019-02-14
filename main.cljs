; (require '[patcher.core :refer [apply-patch]])

(defn init-team
    [teams id]
    "Creates a taxi team"
    (assoc teams (keyword id) {
        :from ""
        :to ""
        :time 0
        :cost 0
        :messages []
        :passengers []
    })
)

(defn update-team
    [teams id new-team]
    "Updates an existing team"
    (let [id-keyword (keyword id)]
        (-> teams
            (assoc id-keyword (merge (get teams id-keyword) new-team))
        )
    )
)

(defn join-team
    [teams id name]
    "Join existing team"
    (let [id-keyword (keyword id)
          team (get teams id-keyword)
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
          team (get teams id-keyword)
          messages (get team :messages)
          new-messages (conj messages message)]
        (-> teams
            (update-team id (merge team {:messages new-messages}))
        )
    )
)

(print (init-team {} "123456789"))
(print "\n")
(print (update-team (init-team {} "123456789") "123456789" {:from "Skoltech" :to "Moscow"}))
(print "\n")
(print (join-team (update-team (init-team {} "123456789") "123456789" {:from "Skoltech" :to "Moscow"}) "123456789" "Artem"))
(print "\n")
(print (-> (send-message (update-team (init-team {} "123456789") "123456789" {:from "Skoltech" :to "Moscow"}) "123456789" "Hello, World!")
           (send-message "123456789" "Hello, Artem!")
           (send-message "123456789" "What's the deadline for HW1?")
           (send-message "123456789" "It has expired already!")
       )
)
