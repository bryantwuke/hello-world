package roomsale

class Test_subject {
    String          subject_name                   //考试科目

    static constraints = {
        subject_name(nullable: false, maxSize: 20)
    }

    static mapping = {
        id(generator: 'sequence', params: [sequence: 'Test_subject_seq'])
    }

}
