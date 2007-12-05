class UrlMappings {
    static mappings = {
        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        // setup the blog name...
        "/$blogname/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        "/$blog/$year/$month?/$day?/$id?" {
            controller = "blog"
            action = "displayOneEntry"
            constraints {
                year(matches:/\d{4}/)
                month(matches:/\d{2}/)
                day(matches:/\d{2}/)
            }
        }
    }	
}