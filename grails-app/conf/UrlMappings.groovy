class UrlMappings {
    
    static mappings = {

        "/$controller/$action?/$id?" {
            constraints {
                // apply constraints here
            }
        }

        // setup the blog name...
        "/$blog/$controller/$action?/$id?" {
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

        "/$blog/pages/$id**.html" {
            controller = "blog"
            action = "displayStaticEntry"
        }

        // feeds for all blog entries
        "/$blog/$feedtype**.xml" {
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

        "/$blog/admin/stats" {
            controller = "admin"
            action = "stats"
        }

        "/$blog/admin/properties" {
            controller = "blog"
            action = "properties"
        }

       "/$blog/admin/updateProperties" {
            controller = "blog"
            action = "updateProperties"
        }

        "/$blog/admin/drafts" {
            controller = "admin"
            action = "drafts"
        }

        "/$blog/admin/static" {
            controller = "admin"
            action = "staticEntries"
        }

        "/$blog/admin/blog/$action/$id?" {
            controller = "blogEntry"
        }


        "/$blog/admin/comments/pending" {
            controller = "admin"
            action = "pendingComments"
        }

        "/$blog/admin/comments/approved" {
            controller = "admin"
            action = "approvedComments"
        }

        "/$blog/admin/login/$action" {
            controller = "login"
        }


        "/$blog/archive/$tagName?" {
            controller = "blog"
            action = "archive"
        }

        "/$blog/timeline" {
            controller = "blog"
            action = "timeline"
        }

        "/$blog/timelineData" {
            controller = "blog"
            action = "timelineData"
        }


        "/$blog/search" {
            controller = "blog"
            action = "search"
        }
        


        // image display stuff. Mostly /glen/images/stuff.gif but historically /glen/2007/12/images/stuff.gif
        "/$blog/images/$dir/$file**" {
            controller = "image"
            action = "display"
        }

        "/$blog/images/$file**" {
            controller = "image"
            action = "display"
        }

        // image display stuff. Mostly /glen/images/stuff.gif but historically /glen/2007/12/images/stuff.gif
        "/$blog/**/images/$dir/$file**" {
            controller = "image"
            action = "display"
        }

        "/$blog/**/images/$file**" {
            controller = "image"
            action = "display"
        }

        
        "/favicon**" {
            controller = "image"
            action = "display"
        }


        // home page
        "/$blog" {
            controller = "blog"
            action = "homePage"
        }

       "505"(view:"niceError")
       
    }
}