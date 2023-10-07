//package datum.module;
//
//
//
//public class Main {
//    public static void main(String[] args) {
//        //         Document document;
//        // try{
//        //     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//        //     DocumentBuilder builder = factory.newDocumentBuilder();
//        //     document = builder.parse(new File("icd_10_v2019.xml"));
//        // } catch (Exception e){
//        //     throw new RuntimeException(e);
//        // }
//        // document.getDocumentElement().normalize();
//        // Element root = document.getDocumentElement();
//        // Node childNode = root.getFirstChild();
//
//        ICD10CodesManipulator icd = new ICD10CodesManipulator();
//        var chapterList = icd.getAllCodes();
//        chapterList.forEach(chapter -> {
//            // if(icd.isChapter(chapter))
//            // if(icd.isBlock(chapter))
//            // if(icd.isCategory(chapter))
//            if(icd.isSubcategory(chapter))
//                System.out.println(chapter + " - " + icd.getDescription(chapter));
//        });
//    }
//}
