pipeline {
    agent any

    tools {
        // Tên 'jdk11' và 'maven3' phải khớp với tên bạn đặt trong Global Tool Configuration của Jenkins
        jdk 'jdk11'
        maven 'maven3'
    }

    triggers {
        // Chạy mỗi giờ một lần, vào một phút ngẫu nhiên trong giờ
        cron('H * * * *')
    }

    stages {
        stage('Checkout') {
            steps {
                // Lấy code từ repository
                checkout scm
            }
        }

        stage('Build & Test') {
            steps {
                // Chạy test và tạo ra kết quả cho Allure
                sh 'mvn clean test'
            }
        }

        stage('Publish Allure Report') {
            steps {
                // Tạo và hiển thị báo cáo Allure trên giao diện Jenkins
                allure includeProperties: false, jdk: 'jdk11', results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        // Luôn luôn thực hiện các bước này sau khi pipeline kết thúc, dù thành công hay thất bại
        always {
            script {
                // Gửi email thông báo
                emailext (
                    to: 'ducanhdhtb@gmail.com',
                    subject: "Jenkins Build: ${currentBuild.fullDisplayName} - ${currentBuild.currentResult}",
                    body: """<p>Kiểm tra kết quả build cho dự án: <strong>${env.JOB_NAME}</strong></p>
                             <p>Build Number: <strong>${env.BUILD_NUMBER}</strong></p>
                             <p>Trạng thái: <strong>${currentBuild.currentResult}</strong></p>
                             <p>Xem chi tiết tại: <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                             <p>Xem báo cáo Allure: <a href="${env.BUILD_URL}allure/">${env.BUILD_URL}allure/</a></p>
                          """,
                    mimeType: 'text/html'
                )
            }
        }
    }
}
