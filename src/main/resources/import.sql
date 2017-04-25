#CATEGORY
INSERT INTO category (categoryName, categoryURL, status) VALUES ('Dolls', 'dolls', true); #1
INSERT INTO category (categoryName, categoryURL, status) VALUES ('Construction', 'construction', true); #2
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Monster High', 'monster_high', true, 1); #3
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('LEGO', 'lego', true, 2); #4
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Party series', 'monster_high_party', true, 3); #5
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Electrified', 'monster_high_electrified', true, 3); #6
INSERT INTO category (categoryName, categoryURL, status) VALUES ('InActiveCategory', 'inactive', false); #7inactive

#PRODUCTS
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured, longDesc, shortDesc, meta_title, meta_description) VALUES ('FRANKIE STEIN', '59.99', '6', 'PI90800', 'frankie_stein', 5,1,1, "<p>See more snippets like these online store reviews at <a target='_blank' href='http://bootsnipp.com'>Bootsnipp - http://bootsnipp.com</a>.</p><p>Want to make these reviews work? Check out <strong><a href='http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/'>this building a review system tutorial</a></strong>over at maxoffsky.com!</p><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>", "Frankie Stein is a wholehearted girl, and pretty-in-personality kind of girl. She is optimistic, positive, kindhearted and always gets along with others. Frankie is aware of her flaws and embraces them.", "JShop Draculaura Doll", "She is optimistic, positive, kindhearted and always gets along with others.");
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured, longDesc, shortDesc) VALUES ('DRACULAURA', '99.99', '6', 'PI90870','draculaura', 6,1,1, "<p>See more snippets like these online store reviews at <a target='_blank' href='http://bootsnipp.com'>Bootsnipp - http://bootsnipp.com</a>.</p><p>Want to make these reviews work? Check out <strong><a href='http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/'>this building a review system tutorial</a></strong>over at maxoffsky.com!</p><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>", "Draculaura is used to being pampered. She is prone to crying when she has to deal with obstacles, which on a bad day includes not being able to check her apperance in the mirror, though over the past years she has grown to follow that up with action. ");
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured, longDesc, shortDesc) VALUES ('Lego Star', '9.99', '20', 'PI90899','lego_star', 4,1,1, "<p>See more snippets like these online store reviews at <a target='_blank' href='http://bootsnipp.com'>Bootsnipp - http://bootsnipp.com</a>.</p><p>Want to make these reviews work? Check out <strong><a href='http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/'>this building a review system tutorial</a></strong>over at maxoffsky.com!</p><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>", "Win the battle for the Empire with the awesome Death Star!");
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured) VALUES ('Inactive Product', '9.99', '20', 'PI90899','inactive_product', 4,0,1);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured) VALUES ('Category INACTIVEProduct', '9.99', '20', 'PI90899','categ_inactive_product', 7,1,1);