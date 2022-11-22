package tsdw.rest.esami;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import tsdw.rest.esami.domain.Exam;
import tsdw.rest.esami.repository.ExamRepository;
import tsdw.rest.esami.service.ExamService;

@SpringBootApplication
public class EsamiApplication implements CommandLineRunner {

   @Autowired
   ExamService es;

   @Autowired
   ExamService es2;

   @Autowired
   ExamRepository er;

   @Override
   public void run(String... args) throws Exception {
      System.out.println("\nIn run()");
      Exam e = new Exam("Analisi 2", new Date());
      es.addExam(e);
      Exam ee = new Exam("Analisi 5", new Date());
      er.save(ee);

      es.getAllExams().forEach(x -> System.out.println(x));

      System.out.println(es == es2);
      System.out.println("es = " + es);

   }

   public static void main(String[] args) {
      System.out.println("\nIn main()");
      ApplicationContext ctx = SpringApplication.run(EsamiApplication.class, args);
      ExamService es1 = (ExamService) ctx.getBean(ExamService.class);
      System.out.println("es1 = " + es1);

      Exam e = new Exam("Analisi 3", new Date());
      es1.addExam(e);
      es1.getAllExams().forEach(x -> System.out.println(x));
      // for (String s : ctx.getBeanDefinitionNames())
      // System.out.println(s);
      ExamRepository er1 = (ExamRepository) ctx.getBean(ExamRepository.class);
      Exam e2 = new Exam("Analisi 4", new Date());
      er1.save(e2);
   }
}