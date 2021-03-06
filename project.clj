(defproject clojure2d "1.0.0-RC2"
  :description "Creative coding / glitch library backed by Java2D"
  :url "https://github.com/Clojure2D/clojure2d"
  :license {:name "MIT"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [generateme/fastmath "1.0.1"]
                 [thi.ng/color "1.2.0"] ;; iq gradients
                 ;; [org.bytedeco/javacv-platform "1.4.1"]
                 ]
  :resource-path "resources/"
  :java-source-paths ["src"]
  :javac-options ["-target" "1.8" "-source" "1.8"]
  :prep-tasks [["compile" "fastmath.vector"] "javac"]
  :scm {:name "git"
        ::url "https://github.com/Clojure2D/clojure2d.git"}
  :repl-options {:timeout 120000}
  :target-path "target/%s"
  :jvm-opts ["-Xmx2048M"
             ;; "-Dcom.sun.management.jmxremote"
             ;; "-Dcom.sun.management.jmxremote.ssl=false"
             ;; "-Dcom.sun.management.jmxremote.authenticate=false"
             ;; "-Dcom.sun.management.jmxremote.port=43210"
             ]
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[refactor-nrepl "2.4.0-SNAPSHOT"]
                             [cider/cider-nrepl "0.18.0-SNAPSHOT"]
                             [lein-ancient "0.6.15"]
                             [lein-kibit "0.1.6"]
                             [lein-bikeshed "0.5.0"]
                             [com.jakemccrary/lein-test-refresh "0.22.0"]
                             [lein-codox "0.10.3"]]
                   :source-paths ["example"]
                   :dependencies [[codox-theme-rdash "0.1.2"]
                                  [criterium "0.4.4"]
                                  [metadoc "0.2.3"]
                                  [hiccup "1.0.5"]]
                   :codox {:themes [:rdash]
                           :metadata {:doc/format :markdown}
                           :output-path "docs/codox/"
                           :source-paths ["src"]
                           :source-uri "https://github.com/Clojure2D/clojure2d/blob/master/{filepath}#L{line}"
                           :doc-paths ["docs/tutorials/"]
                           :writer metadoc.writers.codox/write-docs
                           :html {:transforms [[:head] [:append [:script {:type "text/javascript",
                                                                          :async ""
                                                                          :src "https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.2/MathJax.js?config=TeX-MML-AM_CHTML"}]]]}}}})
