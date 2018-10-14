package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import service.HtmlDataService;

/**
 * Servlet Filter implementation class InitFilter
 */
@WebFilter("/InitFilter")
public class InitFilter implements Filter {
	
	public void init(FilterConfig fConfig) throws ServletException {
		Thread t = new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						HtmlDataService.updateHome();
						HtmlDataService.updateLeaderboard();
						HtmlDataService.updateChasing();
						Thread.sleep(1000 * 60 * 60);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		};
		t.start();
	}

	public InitFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		chain.doFilter(request, response);
	}

}
