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
                year(matches: /\d{4}/)
                month(matches: /\d{2}/)
                day(matches: /\d{2}/)
            }
        }

        // feeds for all blog entries
        "/$blog/$feedtype" {
            controller = "blog"
            action = "feeds"
            constraints {
                feedtype(inList: ['rss', 'atom'])
            }
        }

        "/$blog/stats" {
            controller = "blog"
            action = "stats"
        }

        "/$blog/admin/comments/pending" {
            controller = "blog"
            action = "pendingComments"
        }

        "/$blog/admin/comments/approved" {
            controller = "blog"
            action = "approvedComments"
        }

        "/$blog/archive" {
            controller = "blog"
            action = "archive"
        }

        // feeds for individual categories
        "/$blog/categories/$categoryName/$feedtype" {
            controller = "blog"
            action = "feeds"
            constraints {
                feedtype(inList: ['rss', 'atom'])
            }
        }

        // image display stuff. Mostly /glen/images/stuff.gif but historically /glen/2007/12/images/stuff.gif 
        "/$blog/**/images/**" {
            controller = "image"
            action = "display"
        }
    }
}