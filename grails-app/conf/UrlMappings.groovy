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
            controller = "feed"
            action = "feeds"
            constraints {
                feedtype(inList: ['rss', 'atom'])
            }
        }

        // feeds for individual categories
        "/$blog/categories/$categoryName/$feedtype" {
            controller = "feed"
            action = "feeds"
            constraints {
                feedtype(inList: ['rss', 'atom'])
            }
        }

        "/$blog/stats" {
            controller = "admin"
            action = "stats"
        }

        "/$blog/admin/comments/pending" {
            controller = "admin"
            action = "pendingComments"
        }

        "/$blog/admin/comments/approved" {
            controller = "admin"
            action = "approvedComments"
        }

        "/$blog/admin/login/form" {
            controller = "login"
            action = "form"
        }

        "/$blog/admin/login/login" {
            controller = "login"
            action = "login"
        }

        "/$blog/admin/login/logout" {
            controller = "login"
            action = "logout"
        }


        "/$blog/archive/$tagName?" {
            controller = "blog"
            action = "archive"
        }



        // image display stuff. Mostly /glen/images/stuff.gif but historically /glen/2007/12/images/stuff.gif 
        "/$blog/**/images/**" {
            controller = "image"
            action = "display"
        }

        // image display stuff. Mostly /glen/images/stuff.gif but historically /glen/2007/12/images/stuff.gif
        "/$blog/images/**" {
            controller = "image"
            action = "display"
        }

        // home page
        "/$blog" {
            controller = "blog"
            action = "homePage"
        }
    }
}