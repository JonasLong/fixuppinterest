package com.pinfix.fixuppinterest;

public abstract class FormatUtils {
    private static final String HTML_FORMAT_STRING = """
		<!DOCTYPE html>
			<head>
				<meta property="og:title" content="{title}">
				<meta property="og:description" content="{description}">
				<meta property="og:image" content="{imageUrl}">
				
				<title>
					{title}
				</title>
			</head>
			<body>
				<h1>{title}</h1>
				<h2>{description}</h2>
				<p><a href="{srcUrl}"><strong>View this post on Pinterest</strong></a></p>

				<img src="{imageUrl}" />
			</body>
		</html>
		""";

	private static final String HMTL_ERROR_MESSAGE = """
		<!DOCTYPE html>
			<head>
				<meta property="og:title" content="This post cannot be embedded">
				<meta property="og:description" content="{reason}">
				
				<title>Error | Pinfix</title>
			</head>
			<body>
				<h1>This post is not available</h1>
				<h2>{reason}</h2>
			</body>
		</html>
		""";

	private static final String HTML_HOMEPAGE = """
		<!DOCTYPE html>
			<head>
				<meta property="og:title" content="Welcome to Pinfix">
				<meta property="og:description" content="Append a post id to this link in order to embed it">
				
				<title>Error | Pinfix</title>
			</head>
			<body>
				<h1>Welcome to Pinfix</h1>
				<h2>Append a post id to this link in order to embed it</h2>
			</body>
			</html>
		""";

	public static String errorPage(String reason){
		String page = HMTL_ERROR_MESSAGE;
		return page.replaceAll("{reason}", reason);
	}

	public static String getHomepage(){
		return HTML_HOMEPAGE;
	}
    
    public static String formatPage(Post post){
        String page = HTML_FORMAT_STRING;

		return page.replace("{title}", post.title)
			.replace("{description}", post.description)
			.replace("{imageUrl}", post.imageUrl)
			.replace("{srcUrl}", post.srcUrl);
    }

}
