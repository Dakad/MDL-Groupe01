
INSERT INTO USER(ID, CREATED_AT, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, USERNAME, PROFIL_ID) VALUES
(1, NULL, 'root@mail.dom', 'Root', 'Mdl', '$2a$10$JrrGxRCes4ncJ5u.Y1Qr.e/dAO31T23uTVtyDXFRjytV/iu3LfgTW', 'Groot', NULL),
(2, NULL, 'std@mail.dom', 'Std', 'Mdl', '$2a$10$Zy0r2byvDnkM2LYv.e7.SOhuXyk1F4YXfPHYCKuvuHakV4tJTuRu.', 'Std', NULL);

INSERT INTO TAG(ID, NAME, SLUG) VALUES
(3, 'Computer science', 'computer-science'),
(4, 'Physics', 'physics'),
(5, 'Big data', 'big-data'),
(6, 'Public interface', 'public-interface'),
(7, 'A.I.', 'a-i'),
(8, 'Air pollution', 'air-pollution'),
(9, 'Analytics', 'analytics'),
(17, 'spatio-temporal databases', 'spatio-temporal-databases'),
(18, 'data streams', 'data-streams'),
(19, 'best region search', 'best-region-search'),
(20, 'road network', 'road-network'),
(26, 'literature review', 'literature-review'),
(27, 'value realization', 'value-realization'),
(28, 'portability', 'portability'),
(29, 'interconnectivity', 'interconnectivity'),
(33, 'big data analytics', 'big-data-analytics'),
(34, 'big data definition', 'big-data-definition'),
(35, 'unstructured data analytics', 'unstructured-data-analytics'),
(36, 'predictive analytics', 'predictive-analytics'),
(50, 'near duplicate video', 'near-duplicate-video'),
(51, 'general system', 'general-system'),
(52, 'real-time processing', 'real-time-processing'),
(53, 'retrieval and detection', 'retrieval-and-detection'),
(57, 'ambient displays', 'ambient-displays'),
(58, 'interactive public displays', 'interactive-public-displays'),
(59, 'subtle interaction', 'subtle-interaction'),
(60, 'ubicomp', 'ubicomp');  


-- 1 +/- SELECT COUNT(*) FROM ARTICLE;
INSERT INTO ARTICLE(ID, KAKA, CREATED_AT, JOURNAL, JOURNAL_NUMBER, JOURNAL_VOLUME, NB_CITATIONS, NB_VIEWS, PAGES, PRICE, PUBLICATION_MONTH, PUBLICATION_YEAR, PUBLISHER, REFERENCE, TITLE, URL, CATEGORY_ID, CREATOR_USER_ID) VALUES
(10, 'A GIS based component-oriented integrated system for estimation, visualization and analysis of road traffic air pollution', DATE '2019-04-11', 'Environmental Modelling & Software', '6', '14', 0, 0, '531--539', 0.0, NULL, 1999, 'Elsevier', 'rebolj1999gis', 'A GIS based component-oriented integrated system for estimation, visualization and analysis of road traffic air pollution', 'https://www.sciencedirect.com/science/article/pii/S1364815299000171', 3, 2),
(13, 'Given a set of spatial objects O, the search radius r and the submodular function f are specified by the user. The best region search (BRS) is to find an optimal area with fixed range size, in which the object set has the maximum submodular function value. The BRS problem has long been studied because of its wide application in spatial data mining, facility locating and so on. However, most of the existing work focus on either Euclidean space or motionless objects, which is not applicable in many real-life cases. In this paper, we propose the best region monitoring problem in the spatial data streams in road networks (MBRS). Many real life applications can obtain benefit from MBRS problem, such as monitoring traffic and tracking in ecology. We first propose an efficient algorithm to the static BRS problem in road networks, and extend the solution to a naive method to solve the MBRS problem. Then, we put forward effective pruning strategies and branch-and-bound algorithm GER on the basis of the preprocessing to monitor best region at different times. Finally, a large number of experiments verify the efficiency of the proposed method.', DATE '2019-04-11', 'Data & Knowledge Engineering', NULL, '120', 0, 0, '100 - 118', 0.0, NULL, 2019, NULL, 'CHEN2019100', 'Monitoring best region in spatial data streams in road networks', 'http://www.sciencedirect.com/science/article/pii/S0169023X18303951', 9, 1),
(21, 'Big data has been considered to be a breakthrough technological development over recent years. Notwithstanding, we have as yet limited understanding of how organizations translate its potential into actual social and economic value. We conduct an in-depth systematic review of IS literature on the topic and identify six debates central to how organizations realize value from big data, at different levels of analysis. Based on this review, we identify two socio-technical features of big data that influence value realization: portability and interconnectivity. We argue that, in practice, organizations need to continuously realign work practices, organizational models, and stakeholder interests in order to reap the benefits from big data. We synthesize the findings by means of an integrated model.', DATE '2019-04-11', 'The Journal of Strategic Information Systems', '3', '26', 0, 0, '191 - 209', 0.0, NULL, 2017, NULL, 'GUNTHER2017191', 'Debating big data: A literature review on realizing value from big data', 'http://www.sciencedirect.com/science/article/pii/S0963868717302615', 3, 1),
(30, 'Size is the first, and at times, the only dimension that leaps out at the mention of big data. This paper attempts to offer a broader definition of big data that captures its other unique and defining characteristics. The rapid evolution and adoption of big data by industry has leapfrogged the discourse to popular outlets, forcing the academic press to catch up. Academic journals in numerous disciplines, which will benefit from a relevant discussion of big data, have yet to cover the topic. This paper presents a consolidated description of big data by integrating definitions from practitioners and academics. The paper''s primary focus is on the analytic methods used for big data. A particular distinguishing feature of this paper is its focus on analytics related to unstructured data, which constitute 95% of big data. This paper highlights the need to develop appropriate and efficient analytical methods to leverage massive volumes of heterogeneous data in unstructured text, audio, and video formats. This paper also reinforces the need to devise new tools for predictive analytics for structured big data. The statistical methods in practice were devised to infer from sample data. The heterogeneity, noise, and the massive size of structured big data calls for developing computationally efficient algorithms that may avoid big data pitfalls, such as spurious correlation.', DATE '2019-04-11', 'International Journal of Information Management', '2', '35', 0, 0, '137 - 144', 0.0, NULL, 2015, NULL, 'GANDOMI2015137', 'Beyond the hype: Big data concepts, methods, and analytics', 'http://www.sciencedirect.com/science/article/pii/S0268401214001066', 3, 1);    
INSERT INTO ARTICLE(ID, KAKA, CREATED_AT, JOURNAL, JOURNAL_NUMBER, JOURNAL_VOLUME, NB_CITATIONS, NB_VIEWS, PAGES, PRICE, PUBLICATION_MONTH, PUBLICATION_YEAR, PUBLISHER, REFERENCE, TITLE, URL, CATEGORY_ID, CREATOR_USER_ID) VALUES
(37, 'Dynamo: a public interactive surface supporting the cooperative sharing and exchange of media', DATE '2019-04-11', 'Proceedings of the 16th annual ACM symposium on User interface software and technology', NULL, NULL, 0, 0, '159--168', 0.0, NULL, 2003, NULL, 'izadi2003dynamo', 'Dynamo: a public interactive surface supporting the cooperative sharing and exchange of media', 'http://doi.acm.org/10.1145/964696.964714', 6, 2),
(43, 'GVoS: A General System for Near-Duplicate Video-Related Applications on Storm', DATE '2019-04-11', 'ACM Trans. Inf. Syst.', '1', '36', 0, 0, '3:1--3:36', 0.0, 'jun', 2017, 'ACM', 'Jiang:2017:GGS:3077622.3041657', 'GVoS: A General System for Near-Duplicate Video-Related Applications on Storm', 'http://doi.acm.org/10.1145/3041657', 7, 1),
(54, 'Interactive Public Ambient Displays: Transitioning from Implicit to Explicit, Public to Personal, Interaction with Multiple Users', DATE '2019-04-11', 'Proceedings of the 17th Annual ACM Symposium on User Interface Software and Technology', NULL, NULL, 0, 0, '137--146', 0.0, NULL, 2004, 'ACM', 'Vogel:2004:IPA:1029632.1029656', 'Interactive Public Ambient Displays: Transitioning from Implicit to Explicit, Public to Personal, Interaction with Multiple Users', 'http://doi.acm.org/10.1145/1029632.1029656', 6, 1);               


-- 2 +/- SELECT COUNT(*) FROM AUTHOR;
INSERT INTO AUTHOR(ID, NAME, SLUG) VALUES
(11, 'Rebolj Danijel', 'rebolj-danijel'),
(12, 'Sturm Peter J', 'sturm-peter-j'),
(14, 'Zijun Chen', 'zijun-chen'),
(15, 'Qin Yuan', 'qin-yuan'),
(16, 'Wenyuan Liu', 'wenyuan-liu'),
(22, STRINGDECODE('Wendy Arianne G\u00fcnther'), 'wendy-arianne-guenther'),
(23, 'Mohammad H. Rezazade Mehrizi', 'mohammad-h-rezazade-mehrizi'),
(24, 'Marleen Huysman', 'marleen-huysman'),
(25, 'Frans Feldberg', 'frans-feldberg'),
(31, 'Amir Gandomi', 'amir-gandomi'),
(32, 'Murtaza Haider', 'murtaza-haider'),
(38, 'Izadi Shahram', 'izadi-shahram'),
(39, 'Brignull Harry', 'brignull-harry'),
(40, 'Rodden Tom', 'rodden-tom'),
(41, 'Rogers Yvonne', 'rogers-yvonne'),
(42, 'Underwood Mia', 'underwood-mia'),
(44, 'Jiang Jiawei', 'jiang-jiawei'),
(45, 'Tong Yunhai', 'tong-yunhai'),
(46, 'Lu Hua', 'lu-hua'),
(47, 'Cui Bin', 'cui-bin'),
(48, 'Lei Kai', 'lei-kai'),
(49, 'Yu Lele', 'yu-lele'),
(55, 'Vogel Daniel', 'vogel-daniel'),
(56, 'Balakrishnan Ravin', 'balakrishnan-ravin');   


INSERT INTO ARTICLE_KEYWORDS(ARTICLE_ID, TAG_ID) VALUES
(13, 17),
(13, 18),
(13, 20),
(13, 19),
(21, 9),
(21, 5),
(21, 28),
(21, 27),
(21, 29),
(21, 26),
(30, 9),
(30, 5),
(30, 34),
(30, 33),
(30, 35),
(30, 36),
(37, 6),
(43, 51),
(43, 50),
(43, 53),
(43, 52),
(54, 58),
(54, 60),
(54, 59),
(54, 57),
(54, 6); 

INSERT INTO ARTICLE_AUTHORS(AUTHOR_ID, ARTICLE_ID) VALUES
(10, 11),
(10, 12),
(13, 15),
(13, 16),
(13, 14),
(21, 24),
(21, 22),
(21, 25),
(21, 23),
(30, 31),
(30, 32),
(37, 42),
(37, 39),
(37, 40),
(37, 41),
(37, 38),
(43, 46),
(43, 48),
(43, 47),
(43, 49),
(43, 45),
(43, 44),
(54, 56),
(54, 55);

