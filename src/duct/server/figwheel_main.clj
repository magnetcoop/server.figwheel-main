(ns duct.server.figwheel-main
  (:require [figwheel.main.api :as figwheel]
            [integrant.core :as ig]))

(defmethod ig/init-key :duct.server/figwheel-main [_ opts]
  (figwheel/start opts)
  {:server (:id opts)})

(defmethod ig/halt-key! :duct.server/figwheel-main [_ {:keys [server]}]
  (figwheel/stop server))

(defmethod ig/suspend-key! :duct.server/figwheel-main [_ _])

(defmethod ig/resume-key :duct.server/figwheel-main [key opts old-opts old-impl]
  (if (and (:server old-impl) (= opts old-opts))
    old-impl
    (do (ig/halt-key! key old-impl)
        (ig/init-key key opts))))
